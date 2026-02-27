#!/bin/sh
CONSOLE="/dev/tty1"

# Clear screen
clear > $CONSOLE

# 2. Print your ASCII logo (Insert your ASCII art here)
cat << "EOF" > /etc/issue

MM  MM  EEEEEE  RRRR    LL      II  NN  N   OOO    SSSS  
M MM M  EE      R   R   LL      II  N N N  O   O  S      
M    M  EEEE    RRRR    LL      II  N  NN  O   O   SSS   
M    M  EE      R  R    LL      II  N   N  O   O      S
M    M  EEEEEE  R   R   LLLLLL  II  N   N   OOO   SSSS                                                                                                                                                                                                                                                                                                                                       
                                                      
UElement Softwares / MerlinOS v1.0
EOF


# 2. Print your ASCII logo (Insert your ASCII art here)
# cat << "EOF" > /etc/issue

# M   M  EEE  RRR  L   I  N N  OOO  SSS   L   I  TTT  EEE
# MM MM  E    R  R L   I  NNN  O O  S     L   I   T   E
# M M M  EE   RRR  L   I  N N  O O  SSS   L   I   T   EE
# M   M  E    R R  L   I  N N  O O    S   L   I   T   E
# M   M  EEE  R  R LLL I  N N  OOO  SSS   LLL I   T   EEE      
 
                                                      
# UElement Softwares / MerlinLiteOS v1.0
# EOF



# 3. Silence kernel noise to prevent it from overwriting the logo
dmesg -n 1

# 4. Redirect all FUTURE bt noise tnothingne
exe>/dev/null 2>&1