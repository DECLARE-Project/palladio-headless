package de.fabiankeller.palladio.builder;

import de.fabiankeller.palladio.environment.PalladioEclipseEnvironment;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.analyzer.workflow.blackboard.PCMResourceSetPartition;
import org.palladiosimulator.analyzer.workflow.configurations.AbstractPCMWorkflowRunConfiguration;
import org.palladiosimulator.pcm.allocation.Allocation;
import org.palladiosimulator.pcm.repository.*;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;
import org.palladiosimulator.pcm.resourceenvironment.ResourceEnvironment;
import org.palladiosimulator.pcm.resourcetype.ResourceRepository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.solver.context.aggregatedUsageContext.ComputedAggregatedUsage;
import org.palladiosimulator.solver.context.computed_allocation.ComputedAllocation;
import org.palladiosimulator.solver.context.computed_usage.ComputedUsage;
import org.palladiosimulator.solver.models.PCMInstance;

import java.util.List;
import java.util.Set;

/**
 * Entry point to building {@link PCMInstance}s.
 */
public class PcmBuilder {



    public PCMInstance build() {
        // create repository
        Repository repository = RepositoryFactory.eINSTANCE.createRepository();
        repository.setEntityName("defaultRepository");

        // add component
        BasicComponent businessTripMgmt = RepositoryFactory.eINSTANCE.createBasicComponent();
        businessTripMgmt.setEntityName("BusinessTripMgmt");
        businessTripMgmt.setRepository__RepositoryComponent(repository);
        repository.getComponents__Repository().add(businessTripMgmt);

        // add operation
        OperationInterface IBusinessTrip = RepositoryFactory.eINSTANCE.createOperationInterface();
        IBusinessTrip.setEntityName("IBusinessTrip");
        IBusinessTrip.setRepository__Interface(repository);
        // add signature to operation
        OperationSignature plan = RepositoryFactory.eINSTANCE.createOperationSignature();
        plan.setEntityName("plan");
        plan.setInterface__OperationSignature(IBusinessTrip);
        // add parameter to signature
        Parameter isBook = RepositoryFactory.eINSTANCE.createParameter();
        isBook.setParameterName("isBook");
            PrimitiveDataType bool = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
            bool.setType(PrimitiveTypeEnum.BOOL);
        isBook.setDataType__Parameter(bool);
        isBook.setOperationSignature__Parameter(plan);
        plan.getParameters__OperationSignature().add(isBook);
        IBusinessTrip.getSignatures__OperationInterface().add(plan);

        // add provided operation to component
        OperationProvidedRole provided_i1_c1 = RepositoryFactory.eINSTANCE.createOperationProvidedRole();
        provided_i1_c1.setEntityName("Provided_I1_C1");
        provided_i1_c1.setProvidedInterface__OperationProvidedRole(IBusinessTrip);
        businessTripMgmt.getProvidedRoles_InterfaceProvidingEntity().add(provided_i1_c1);

        PCMResourceSetPartition rs = new PCMResourceSetPartition();
        rs.initialiseResourceSetEPackages(AbstractPCMWorkflowRunConfiguration.PCM_EPACKAGES);
        rs.getResourceSet().setURIConverter(PalladioEclipseEnvironment.INSTANCE.getUriConverter());
        rs.setContents(URI.createFileURI("/temp/mysuperdefault.repository"), repository);
        rs.getRepositories().add(repository);

        return new PCMInstance(rs);
    }
}
