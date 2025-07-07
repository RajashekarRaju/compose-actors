package com.developersbreach.composeactors.data.auth

import com.amplifyframework.core.AmplifyConfiguration
import com.developersbreach.composeactors.BuildConfig
import org.json.JSONObject

object AmplifyConfigProvider {
    private val json = """
{
  "auth": {
    "plugins": {
      "awsCognitoAuthPlugin": {
        "IdentityManager": {
          "Default": {}
        },
        "CognitoUserPool": {
          "Default": {
            "PoolId": "${BuildConfig.COGNITO_POOL_ID}",
            "AppClientId": "${BuildConfig.COGNITO_APP_CLIENT_ID}",
            "Region": "${BuildConfig.COGNITO_REGION}"
          }
        },
        "Auth": {
          "Default": {
            "authenticationFlowType": "USER_SRP_AUTH",
            "socialProviders": [],
            "usernameAttributes": [],
            "signupAttributes": ["EMAIL"],
            "passwordProtectionSettings": {
              "passwordPolicyMinLength": [8],
              "passwordPolicyCharacters": ["REQUIRES_UPPERCASE", "REQUIRES_LOWERCASE", "REQUIRES_NUMBER"]
            },
            "mfaConfiguration": "OFF",
            "mfaTypes": ["[MFA TYPE]"],
            "verificationMechanisms": ["EMAIL"],
            "OAuth": {
              "WebDomain": "${BuildConfig.COGNITO_WEB_DOMAIN}",
              "AppClientId": "${BuildConfig.COGNITO_APP_CLIENT_ID}",
              "SignInRedirectURI": "com.developersbreach.composeactors://auth",
              "SignOutRedirectURI": "com.developersbreach.composeactors://auth",
              "Scopes": ["email", "openid", "profile"]
            }
          }
        }
      }
    }
  }
}
    """.trimIndent()

    val config = AmplifyConfiguration.fromJson(JSONObject(json))
}