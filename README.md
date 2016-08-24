# HipChat
A sample application that transforms Hipchat's chat message string to JSON format.

## @Mentions
**Input:** "@chris you around? "
- Return (string):
```
{ "mentions": [ "chris" ] }
```

## Emoticons
**Input:** "Good morning! (megusta) (coffee)"
- Return (string):
```
{ "emoticons": [ "megusta", "coffee" ] }
```
## Links
**Input:** "Olympics are starting soon; http://www.nbcolympics.com"
- Return (string):
```
{
"links": [
{ "url": "http://www.nbcolympics.com", "title": "NBC Olympics | 2014 NBC Olympics in Sochi Russia" }
]
}
```

## All
**Input:** "@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016"
- Return (string):
```
{
"mentions": [
"bob",
"john"
],
"emoticons": [
"success"
],
"links": [
{ "url": "https://twitter.com/jdorfman/status/430511497475670016", "title": "Twitter / jdorfman: nice @littlebigdetail from ..." }

]
}
```

# Architecture 
Based on Clean Architecture 

# Technologies used
- Dagger2
- RxJava

