#!/bin/sh

echo
echo --- Scilab example ---------------------------------------------

workingDir=`dirname $0`
. $workingDir/env.sh
PROACTIVE=$workingDir/../..


$JAVACMD org.objectweb.proactive.ext.scilab.$1

echo
echo ------------------------------------------------------------
