#!/bin/sh

echo "Installing Palladio plugin JARs..."
echo "Checking given palladio source directory: ${1}"

# sanity checks
if [ ! -d $1 ]; then
    echo "Error: the Palladio directory does not exist"
    exit 1
fi
if [ ! -d $1/plugins ]; then
    echo "Error: the Palladio directory does not contain a plugins directory. Please provide the path to the Palladio installation."
    exit 1
fi
echo "  Palladio Directory OK"


# build maven repo
mkdir -p ./pathing/.m2/

#for name in $(cat pom.xml | sed -rn "s/^\s*<artifactId>([^<]+).+$/\1/p"); do
for name in $(cat pom.xml | sed -rn '
    /^.*de\.fabiankeller\.palladio<\/groupId>\s*$/{    # if line matches group
        N                                              # read next line into pattern space
        s/.*<artifactId>([^<]+).*/\1/p                 # print artifactId
    }'); do

    # try to find plugin jar file with the given name
    plugin=$(find $1/plugins -maxdepth 1 -type f -name "$name\_*.jar" | tail -n 1)

    if [ -z "$plugin" ]; then
        echo "  WARNING >>> $name could not be found in any version in $1/plugins. Please install the appropriate Palladio extension containing the JAR."
        continue
    fi
    version=$(echo $plugin | sed -rn 's/^[^_]+\_(.*?)\.jar$/\1/p')

    # check if it is already imported
    if [ -f ./pathing/.m2/de/fabiankeller/palladio/$name/$version/$name-$version.pom ]; then
        echo "  Skipping >>> $name :: $version, as pom is already present"
        continue
    fi

    # import as maven dependency
    echo "  Adding >>> $name :: $version"
    mvn install:install-file \
        -Dfile=$plugin \
        -DgroupId="de.fabiankeller.palladio" \
        -DartifactId=$name \
        -Dversion=$version \
        -Dpackaging=jar \
        -DlocalRepositoryPath=./pathing/.m2/
done