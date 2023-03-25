BINARIES="../bin/"

# Install jOOQ binaries.
cd "./lib/jOOQ-3.17.6"
./maven-install.sh
cd "${WORK}"

# Compile Maven project.
mvn clean compile assembly:single


# Remove all subdirectories from build directory.
if [ -d $BINARIES ]
then
    cd $BINARIES
    find . -d -type d -exec rm -rf '{}' \;
    cd "${WORK}"
fi