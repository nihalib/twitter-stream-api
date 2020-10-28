# 🗺 Twitter-Stream-api
## To run in dev environment
Add below keys in user environment variable
* Enable CORS - Add `twitter-web's` localhost
`TWITTER.WEB.ORIGIN=http://localhost:3000`
* Twitter's Aut Token - Copy the auth token from Twitter dev account and add as given below
`TWITTER.API.AUTH=Your key`

### Dev environment endpoints
API will start in port - 8087
* Twitter Rules - http://localhost:8087/twitter-stream/api/v1/rules
* Twitter Stream Feeds - http://localhost:8087/twitter-stream/api/v1/tweets