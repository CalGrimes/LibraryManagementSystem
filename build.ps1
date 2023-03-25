# Set working directories.
$work = $pwd
$binaries = "..\bin\"

.\maven-install.bat
Set-Location -Path $work

# Compile Maven project.
mvn clean compile assembly:single

# Remove all subdirectories from build directory.
if ([System.IO.Directory]::Exists($binaries))
{
    foreach ($dir in [System.IO.Directory]::EnumerateDirectories($binaries))
    {
        [System.IO.Directory]::Delete($dir, $true)
    }
}