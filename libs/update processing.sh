clear

BASEPATH="/Users/jmcouillard/Productions/10 - Java/LightPainting/libs"


# UPDATE FROM SVN
# cd /Users/jmcouillard/svn-git/processing-read-only/
# svn update
# Official beta build 2.0b6 revision is r10380
# Last working before JOGL update revision is r10427
# Currenty using revision r10781 : svn update -r r10781
# svn update


# UPDATE FROM Github
echo "\nUpdating Processing repo..."
cd /Users/jmcouillard/svn-git/processing/
git remote -v update


# BUILD ANT FILES (core.jar, video.jar)
echo "\nBuilding Processing..."
cd "$BASEPATH/processing/"
ant -quiet -f /Users/jmcouillard/svn-git/processing/core/build.xml clean build
ant -quiet -f /Users/jmcouillard/svn-git/processing/java/libraries/video/build.xml clean build
ant -quiet -f /Users/jmcouillard/svn-git/processing/java/libraries/net/build.xml clean build


# COPY CORE JARs
echo "\nCopying Processing libraries..."
cd "$BASEPATH/processing/"
cp /Users/jmcouillard/svn-git/processing/core/library/*.jar ./


# COPY LIBS JARs
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/minim.jar minim.jar
# cp /Users/jmcouillard/svn-git/processing/java/libraries/opengl/library/opengl.jar opengl.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/video/library/video.jar video.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/net/library/net.jar net.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/video/library/gstreamer-java.jar gstreamer-java.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/video/library/jna.jar jna.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/jl1.0.jar jl1.0.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/jsminim.jar jsminim.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/minim.jar minim.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/mp3spi1.9.4.jar mp3spi1.9.4.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/tritonus_aos.jar tritonus_aos.jar
cp /Users/jmcouillard/svn-git/processing/java/libraries/minim/library/tritonus_share.jar tritonus_share.jar
cp -r /Users/jmcouillard/svn-git/processing/java/libraries/video/library/macosx32* macosx32
cp -r /Users/jmcouillard/svn-git/processing/java/libraries/video/library/macosx64* macosx64


# COTRIBUTED LIBS
# eval cd $BASEPATH


# COTRIBUTED LIBS : Ani (local fork)
# eval rm -R $BASEPATH/Ani
# ant -DlibraryClasspath="libs" -f /Users/jmcouillard/svn-git/_github_jmcouillard/Ani/resources/build.xml
# unzip /Users/jmcouillard/svn-git/_github_jmcouillard/Ani/distribution/Ani/Ani.zip -d /Users/jmcouillard/svn-git/_github_jmcouillard/Ani/distribution/Ani/
# cp -r /Users/jmcouillard/svn-git/_github_jmcouillard/Ani/distribution/Ani/Ani Ani


# CREATE SRC ZIPs
echo "\nCopying Processing source for inline reference..."
cd /Users/jmcouillard/svn-git/processing/core/
zip -q -R "$BASEPATH/processing/core_src.zip" "*.java"
cd /Users/jmcouillard/svn-git/processing/java/libraries/video/
zip -q -R "$BASEPATH/processing/video_src.zip" "*.java"




# #######
# SYPHON
# #######


# UPDATE FROM SVN
# cd /Users/jmcouillard/svn-git/syphon-implementations-read-only/
# svn update

# BUILD ANT FILES
#cd $BASEPATH/Syphon/
#ant -k -Dfolder=library -f /Users/jmcouillard/svn-git/syphon-implementations-read-only/Syphon\ Implementations/Processing_2_0/resources/build.xml copy.build.libs generate.structure parse.source compile generate.jar generate.install.library

# COPY LIBS JARs
#cd $BASEPATH/Syphon/
#cp -r /Users/jmcouillard/svn-git/syphon-implementations-read-only/Syphon\ Implementations/Processing_2_0/tmp/Syphon/library* library
#cp -r /Users/jmcouillard/svn-git/syphon-implementations-read-only/Syphon\ Implementations/Processing_2_0/tmp/Syphon/src* src
#cp -r /Users/jmcouillard/svn-git/syphon-implementations-read-only/Syphon\ Implementations/Processing_2_0/tmp/Syphon/examples* examples



echo "\nDone!\n"
