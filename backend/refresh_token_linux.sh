#!/bin/bash

set -e

read -rp "Enter spotify client id: " client_id
read -rp "Enter spotify client secret: " client_secret
read -rp "Enter redirect uri: " redirect_uri
state=$(tr -dc 'a-zA-Z0-9' < /dev/urandom | fold -w 16 | head -n 1)
scope='user-read-private,user-read-email'

echo "Go to the following link in your browser and get code from query string:"
url="https://accounts.spotify.com/authorize?response_type=code&client_id=${client_id}&scope=${scope}&redirect_uri=${redirect_uri}&state=${state}"
xdg-open "$url"

read -rp "Enter code: " code

printf "\nCopy the refresh token paste it into application.yml\n"
resp=$(curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=authorization_code&code=$code&redirect_uri=$redirect_uri&client_id=$client_id&client_secret=$client_secret" https://accounts.spotify.com/api/token 2> /dev/null)
access_token=$(echo "$resp" | jq -r .access_token)
refresh_token=$(echo "$resp" | jq -r .refresh_token)
echo "Refresh Token: $refresh_token"

printf "\nCopy the user id and paste it into application.yml\n"
user_id=$(curl --request GET --url https://api.spotify.com/v1/me --header "Authorization: Bearer $access_token" 2> /dev/null | jq -r .id)
echo "User ID: $user_id"
