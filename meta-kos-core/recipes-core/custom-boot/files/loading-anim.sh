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
                                                      
UElement Softwares / MerlinLiteOS v1.0
EOF

# 3. Silence kernel noise to prevent it from overwriting the logo
dmesg -n 1

# 4. Redirect all FUTURE bt noise tnothingne
exe>/dev/null 2>&1



# cat << "EOF" > /etc/issue                                                                                                                         
                                                                                                                                             
# MMMMMMMM               MMMMMMMM                                       lllllll   iiii                        OOOOOOOOO        SSSSSSSSSSSSSSS 
# M:::::::M             M:::::::M                                       l:::::l  i::::i                     OO:::::::::OO    SS:::::::::::::::S
# M::::::::M           M::::::::M                                       l:::::l   iiii                    OO:::::::::::::OO S:::::SSSSSS::::::S
# M:::::::::M         M:::::::::M                                       l:::::l                          O:::::::OOO:::::::OS:::::S     SSSSSSS
# M::::::::::M       M::::::::::M    eeeeeeeeeeee    rrrrr   rrrrrrrrr   l::::l iiiiiiinnnn  nnnnnnnn    O::::::O   O::::::OS:::::S            
# M:::::::::::M     M:::::::::::M  ee::::::::::::ee  r::::rrr:::::::::r  l::::l i:::::in:::nn::::::::nn  O:::::O     O:::::OS:::::S            
# M:::::::M::::M   M::::M:::::::M e::::::eeeee:::::eer:::::::::::::::::r l::::l  i::::in::::::::::::::nn O:::::O     O:::::O S::::SSSS         
# M::::::M M::::M M::::M M::::::Me::::::e     e:::::err::::::rrrrr::::::rl::::l  i::::inn:::::::::::::::nO:::::O     O:::::O  SS::::::SSSSS    
# M::::::M  M::::M::::M  M::::::Me:::::::eeeee::::::e r:::::r     r:::::rl::::l  i::::i  n:::::nnnn:::::nO:::::O     O:::::O    SSS::::::::SS  
# M::::::M   M:::::::M   M::::::Me:::::::::::::::::e  r:::::r     rrrrrrrl::::l  i::::i  n::::n    n::::nO:::::O     O:::::O       SSSSSS::::S 
# M::::::M    M:::::M    M::::::Me::::::eeeeeeeeeee   r:::::r            l::::l  i::::i  n::::n    n::::nO:::::O     O:::::O            S:::::S
# M::::::M     MMMMM     M::::::Me:::::::e            r:::::r            l::::l  i::::i  n::::n    n::::nO::::::O   O::::::O            S:::::S
# M::::::M               M::::::Me::::::::e           r:::::r           l::::::li::::::i n::::n    n::::nO:::::::OOO:::::::OSSSSSSS     S:::::S
# M::::::M               M::::::M e::::::::eeeeeeee   r:::::r           l::::::li::::::i n::::n    n::::n OO:::::::::::::OO S::::::SSSSSS:::::S
# M::::::M               M::::::M  ee:::::::::::::e   r:::::r           l::::::li::::::i n::::n    n::::n   OO:::::::::OO   S:::::::::::::::SS 
# MMMMMMMM               MMMMMMMM    eeeeeeeeeeeeee   rrrrrrr           lllllllliiiiiiii nnnnnn    nnnnnn     OOOOOOOOO      SSSSSSSSSSSSSSS                                                                                                                                   
                                                                       
# UElement Softwares / MerlinOS v1.0
# EOF