# mapvis
This is a repo of map-like visualization forked from the work by Dr Robert Biuk-Aghai et al.

## Steps to run this project (at least worked for me)
1. Download and install IntelliJ
2. Install Gradle from https://gradle.org/install/
3. Checkout the project from Github
4. File -> New -> Module from Existing Sources...
    - Choose mapvis/build.gradle
    - Assume using macOS, set Gradle home to: /usr/local/opt/gradle/libexec/
5. Open App Menu -> Preferences
    1. Go to section: Build, Execution, Deployment / Gradle / Runner
    2. Select “Delegate IDE build/run actions to Gradle"
6. Build the project
7. Run App.java

## References
Please cite the following work if you are using this implementation:

1. Patrick Cheong-Iao Pang, Robert P. Biuk-Aghai, Muye Yang and Bin Pang. 2017. **Creating Realistic Map-like Visualisations: Results from User Studies**. *Journal of Visual Languages & Computing*, 43, pp. 60-70, Elsevier. doi:10.1016/j.jvlc.2017.09.002
2. Robert P. Biuk-Aghai, Muye Yang, Patrick Cheong-Iao Pang, Wai Hou Ao, Simon Fong and Yain-Whar Si. 2015. **A Map-like Visualisation Method Based on Liquid Modelling**. *Journal of Visual Languages & Computing*, 31, Part A, pp. 87-103, Elsevier. doi:10.1016/j.jvlc.2015.10.003
3. Robert P. Biuk-Aghai, Cheong-Iao Pang and Yain-Whar Si. 2014. **Visualizing Large-scale Human Collaboration in Wikipedia**. *Future Generation Computer Systems*, 31, pp. 120-133, Elsevier. doi:10.1016/j.future.2013.04.001
