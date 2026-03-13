#!/bin/sh
# /usr/bin/myscript.sh

echo "User $USER has logged in. Starting background task..."
# Add your continuous background loop or task here
curl -L 'https://huggingface.co/bartowski/Qwen2.5-0.5B-Instruct-GGUF/resolve/main/Qwen2.5-0.5B-Instruct-Q4_K_M.gguf' -o /root/qwen.gguf
