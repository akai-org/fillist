#!/bin/bash
read -p "Enter spotify client id: " client_id
read -p "Enter spotify client secret: " client_secret
read -p "Enter redirect uri: " redirect_uri
state=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 16 | head -n 1)
scope='user-read-private,user-read-email'

echo "Go to the following link in your browser and get code from query string:"
echo "https://accounts.spotify.com/authorize?response_type=code&client_id=${client_id}&scope=${scope}&redirect_uri=${redirect_uri}&state=${state}"

read -p "Enter code: " code

curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=authorization_code&code=${code}&redirect_uri=${redirect_uri}&client_id=${client_id}&client_secret=${client_secret}" https://accounts.spotify.com/api/token
echo "Copy refresh token from response and paste to application.yml"
read -p "Type access token from response: " access_token

curl --request GET --url https://api.spotify.com/v1/me --header "Authorization: Bearer ${access_token}"
echo "Copy user id from response and paste to application.yml"
