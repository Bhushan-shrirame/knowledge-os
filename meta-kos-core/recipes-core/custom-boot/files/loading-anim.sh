#!/bin/sh
CONSOLE="/dev/tty1"

# Clear screen
clear > $CONSOLE

# Print your fast welcome
echo -e "\n\033[1;32mWelcome to Knowledge OS\033[0m\n" > $CONSOLE

# FORCE THE CURSOR BACK ON
# \033[?25h is the escape code to show the cursor
echo -ne "\033[?25h" > $CONSOLE
setterm -cursor on > $CONSOLE