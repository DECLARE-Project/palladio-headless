package de.fabiankeller.palladio.analysis.tracing;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.solver.models.PCMInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helps to doTrace all {@link NamedElement}s of a PCM model by appending a random suffix (in fact, a {@link UUID}) to
 * their name. You may then retrieve the elements by UUID.
 */
public class PcmModelTrace {

    /**
     * Used to generate a name containing the trace
     */
    private static final String TRACE_FORMAT = "%s__TRACE[%s]";

    private static final String UUID_REGEX = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}";
    /**
     * Used to parse the trace from a name containing a trace
     * <p>
     * The first group captures the UUID.
     */
    private static final String TRACE_REGEX = "/__TRACE\\[(" + UUID_REGEX + ")\\]/i";

    private static final Pattern traceRegex = Pattern.compile(TRACE_REGEX);

    private final PCMInstance pcm;

    private final Map<UUID, NamedElement> traceMap = new HashMap<>();


    private PcmModelTrace(final PCMInstance pcm) {
        this.pcm = pcm;
    }

    /**
     * Adds tracing {@link UUID} to all {@link NamedElement}s of the {@link PCMInstance}.
     * <p>
     * Can be undone with {@link PcmModelTrace::untrace}.
     */
    public static PcmModelTrace trace(final PCMInstance instance) {
        final PcmModelTrace map = new PcmModelTrace(instance);
        doTrace(instance, el -> {
            // add doTrace
            final UUID trace = UUID.randomUUID();
            assert !map.traceMap.containsKey(trace);
            el.setEntityName(String.format(TRACE_FORMAT, el.getEntityName(), trace.toString()));
            map.traceMap.put(trace, el);
        });
        return map;
    }

    /**
     * Reconstructs the {@link PcmModelTrace} object from an already traced {@link PCMInstance}.
     * <p>
     * This method is useful if you want to restore the trace mapping from a serialized {@link PCMInstance}.
     * <p>
     * Note: this method DOES NOT change the given {@link PCMInstance}! It merely reads available trace information.
     */
    public static PcmModelTrace load(final PCMInstance instance) {
        final PcmModelTrace map = new PcmModelTrace(instance);
        doTrace(instance, el -> {
            final UUID trace = extractTrace(el.getEntityName());
            if (null == trace) {
                return;
            }
            assert !map.traceMap.containsKey(trace);
            map.traceMap.put(trace, el);
        });
        return map;
    }

    /**
     * Removes the tracing suffix of all {@link NamedElement}s of the given {@link PCMInstance}.
     * <p>
     * This method reverts the actions done by {@link PcmModelTrace::trace}.
     */
    public static void untrace(final PCMInstance instance) {
        doTrace(instance, el -> {
            el.setEntityName(el.getEntityName().replaceAll(TRACE_REGEX, ""));
        });
    }

    /**
     * Tries to match a trace UUID in the given string.
     */
    public static UUID extractTrace(final String string) {
        final Matcher matcher = traceRegex.matcher(string);
        if (matcher.matches()) {
            return null;
        }
        return UUID.fromString(matcher.group(1));
    }

    /**
     * Applies the given {@link Consumer} to all {@link NamedElement}s stored in the {@link PCMInstance}.
     */
    private static void doTrace(final PCMInstance pcm, final Consumer<NamedElement> callback) {
        pcm.getRepositories().forEach((e) -> doTrace(e.eAllContents(), callback));
        doTrace(pcm.getAllocation().eAllContents(), callback);
        doTrace(pcm.getResourceEnvironment().eAllContents(), callback);
        doTrace(pcm.getSystem().eAllContents(), callback);
        doTrace(pcm.getUsageModel().eAllContents(), callback);
    }

    private static void doTrace(final TreeIterator<EObject> iterator, final Consumer<NamedElement> callback) {
        while (iterator.hasNext()) {
            final EObject obj = iterator.next();

            // only apply to named elements
            if (!(obj instanceof NamedElement)) {
                continue;
            }
            final NamedElement el = (NamedElement) obj;
            callback.accept(el);
        }
    }


    public NamedElement find(final UUID uuid) {
        if (!this.traceMap.containsKey(uuid)) {
            throw new NoSuchElementException(String.format("The UUID '%s' is not part of this doTrace map.", uuid.toString()));
        }
        return this.traceMap.get(uuid);
    }

}
