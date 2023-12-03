#! /bin/pwsh

$client_id = Read-Host -Prompt 'Enter your client id'
$client_secret = Read-Host -Prompt 'Enter your client secret'
$redirect_uri = Read-Host -Prompt 'Enter your redirect uri'
$state = -join ((65..90) + (97..122) + (48..57) | Get-Random -Count 16 | % {[char]$_})
$scope = "user-read-private,user-read-email"

Write-Output "Go to the following link in your browser and get code from query string:"
$url="https://accounts.spotify.com/authorize?response_type=code&client_id=${client_id}&scope=${scope}&redirect_uri=${redirect_uri}&state=${state}"
Start-Process "$url"

$code = Read-Host -Prompt "Enter the code from the query string"

Write-Output "`nCopy the refresh token and paste it into application.yml"
$body = @{
    grant_type = "authorization_code"
    code = $code
    redirect_uri = $redirect_uri
    client_id = $client_id
    client_secret = $client_secret
}
$url = "https://accounts.spotify.com/api/token"
$resp = Invoke-WebRequest -Method 'Post' -Uri $url -Body $body | ConvertFrom-Json

$access_token = $resp | Select-Object -ExpandProperty access_token
$refresh_token = $resp | Select-Object -ExpandProperty refresh_token

Write-Output "Your refresh token: $refresh_token"


Write-Output "`nCopy the user ID and paste it into application.yml"
$headers = @{
    Authorization = "Bearer $access_token"
}
$userResponse = Invoke-RestMethod -Uri 'https://api.spotify.com/v1/me' -Headers $headers
$user_id = $userResponse.id

Write-Host "User ID: $user_id"
