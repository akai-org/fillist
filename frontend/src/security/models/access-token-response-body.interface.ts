export interface AccessTokenResponseBodyInterface {
  accessToken: string
  tokenType: string
  expiresIn: number
  refreshToken: string | null
}
