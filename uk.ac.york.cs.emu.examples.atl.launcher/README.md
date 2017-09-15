# ATL Launcher Java Project
This project allow the execution of a given ATL modules programatically using Java.

## TransformationLauncher.java
When this file is executed, it finds the list of ATL module transformation folders located in "uk.ac.york.eclipse.epsilon.emu.examples.atl/transformations/*".
Each Transformation folder contains transformation source code, an XMI model corresponding to that source code, input and output metamodels.

After loading the transformation source code from each module folder, it execute the transformation on all input models located in "inModels/[TRANSFORMATION_MODULE]/* ". The output of the transformation is stored in output models folder in "expectedModels/[TRANSFORMATION_MODULE]/* "

## Load the config File for each Transformation Module
Each transformation module has a corrisponding Java config file located in "src/uk/ac/york/eclipse/epsilon/emu/examples/atl/launcher/files/*". The user of this project need to provide values for in the Java config file for each transformation module that are important for transformation execution such as input metamodel name/path and output metamodel name/path, etc. The config file is to be used when running TransformationLauncher.java or MutantLauncher.java	