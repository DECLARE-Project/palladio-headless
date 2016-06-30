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

for plugin in $(find $1/plugins -maxdepth 1 -type f -name '*.jar' ); do
    name=$(echo $plugin | sed -rn 's/^.+\/([^_]+)\_.*?$/\1/p')
    version=$(echo $plugin | sed -rn 's/^[^_]+\_(.*?)\.jar$/\1/p')
    echo "  Adding >>> $name :: $version"

    mvn install:install-file \
        -Dfile=$plugin \
        -DgroupId="de.fabiankeller.palladio" \
        -DartifactId=$name \
        -Dversion=$version \
        -Dpackaging=jar \
        -DlocalRepositoryPath=./pathing/.m2/
done