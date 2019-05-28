javac *.java

if [ ! -d "experiments" ]; then
  mkdir experiments
fi

xargs < parameters.txt -n10 java World
