# api v0.0.0



- [Actor](#actor)
	- [Create actor](#create-actor)
	- [Delete actor](#delete-actor)
	- [Retrieve actor](#retrieve-actor)
	- [Retrieve actors](#retrieve-actors)
	- [Update actor](#update-actor)
	
- [Auth](#auth)
	- [Authenticate](#authenticate)
	- [Authenticate with Google](#authenticate-with-google)
	
- [Collection](#collection)
	- [Create collection](#create-collection)
	- [Delete collection](#delete-collection)
	- [Retrieve collection](#retrieve-collection)
	- [Retrieve collections](#retrieve-collections)
	- [Get All Media by Collection Id](#get-all-media-by-collection-id)
	- [Update collection](#update-collection)
	- [Updates Collection Collected list with mediaId by removing or adding](#updates-collection-collected-list-with-mediaid-by-removing-or-adding)
	
- [Episode](#episode)
	- [Create episode](#create-episode)
	- [Delete episode](#delete-episode)
	- [Retrieve episode](#retrieve-episode)
	- [Retrieve episodes](#retrieve-episodes)
	- [Update episode](#update-episode)
	- [Update Logged User&#39;s WatchedEpisodes list by Episode Id](#update-logged-user&#39;s-watchedepisodes-list-by-episode-id)
	- [Update Logged User&#39;s WatchlistedEpisodes list by Episode Id](#update-logged-user&#39;s-watchlistedepisodes-list-by-episode-id)
	
- [Genre](#genre)
	- [Create genre](#create-genre)
	- [Delete genre](#delete-genre)
	- [Retrieve genre](#retrieve-genre)
	- [Retrieve genres](#retrieve-genres)
	- [Update genre](#update-genre)
	
- [Media](#media)
	- [Create media](#create-media)
	- [Delete media](#delete-media)
	- [Retrieve media](#retrieve-media)
	- [Adds an Actor to Media Cast list by Media Id](#adds-an-actor-to-media-cast-list-by-media-id)
	- [Update media](#update-media)
	
- [Movie](#movie)
	- [Create movie](#create-movie)
	- [Delete movie](#delete-movie)
	- [Retrieve movie](#retrieve-movie)
	- [Retrieve movies](#retrieve-movies)
	- [Get All Movies with associated Logged User&#39;s attributes](#get-all-movies-with-associated-logged-user&#39;s-attributes)
	- [Update movie](#update-movie)
	
- [Photo](#photo)
	- [Create photo](#create-photo)
	- [Delete photo](#delete-photo)
	- [Retrieve photo](#retrieve-photo)
	- [Retrieve photos](#retrieve-photos)
	- [Update photo](#update-photo)
	
- [Season](#season)
	- [Create season](#create-season)
	- [Delete season](#delete-season)
	- [Retrieve season](#retrieve-season)
	- [Retrieve seasons](#retrieve-seasons)
	- [Update season](#update-season)
	
- [Series](#series)
	- [Create series](#create-series)
	- [Delete series](#delete-series)
	- [Retrieve series](#retrieve-series)
	- [Get All Series with associated Logged User&#39;s attributes](#get-all-series-with-associated-logged-user&#39;s-attributes)
	- [Update series](#update-series)
	
- [User](#user)
	- [Create user](#create-user)
	- [Delete user](#delete-user)
	- [Retrieve Dashboard Media by User Id](#retrieve-dashboard-media-by-user-id)
	- [Retrieve Only Users in Logged User&#39;s friend list](#retrieve-only-users-in-logged-user&#39;s-friend-list)
	- [Retrieve Logged User Watchlist](#retrieve-logged-user-watchlist)
	- [Retrieve Genre Stats by User Id](#retrieve-genre-stats-by-user-id)
	- [Retrieve Time Stats by User Id](#retrieve-time-stats-by-user-id)
	- [Retrieve current user](#retrieve-current-user)
	- [Show all episodes by Season Id](#show-all-episodes-by-season-id)
	- [Retrieve user](#retrieve-user)
	- [Retrieve users](#retrieve-users)
	- [Update password](#update-password)
	- [Update user](#update-user)
	- [Update Logged User&#39;s Watched list by Media Id](#update-logged-user&#39;s-watched-list-by-media-id)
	


# Actor

## Create actor



	POST /actors


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Actor's name.</p>							|
| picture			| 			|  <p>Actor's picture.</p>							|

## Delete actor



	DELETE /actors/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve actor



	GET /actors/:id


## Retrieve actors



	GET /actors


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update actor



	PUT /actors/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Actor's name.</p>							|

# Auth

## Authenticate



	POST /auth

### Headers

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| Authorization			| String			|  <p>Basic authorization with email and password.</p>							|

### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>Master access_token.</p>							|

## Authenticate with Google



	POST /auth/google


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>Google user accessToken.</p>							|

# Collection

## Create collection



	POST /collections


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| name			| 			|  <p>Collection's name.</p>							|
| description			| 			|  <p>Collection's description.</p>							|
| collected			| 			|  <p>Collection's collected.</p>							|

## Delete collection



	DELETE /collections/:id


## Retrieve collection



	GET /collections/:id


## Retrieve collections



	GET /collections


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Get All Media by Collection Id



	GET /collections/:id/media


## Update collection



	PUT /collections/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| name			| 			|  <p>Collection's name.</p>							|
| description			| 			|  <p>Collection's description.</p>							|
| collected			| 			|  <p>Collection's collected.</p>							|

## Updates Collection Collected list with mediaId by removing or adding



	GET /collections/update/:mediaid


# Episode

## Create episode



	POST /episodes


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Episode's name.</p>							|
| synopsis			| 			|  <p>Episode's synopsis.</p>							|
| airTime			| 			|  <p>Episode's airTime.</p>							|
| duration			| 			|  <p>Episode's duration.</p>							|

## Delete episode



	DELETE /episodes/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve episode



	GET /episodes/:id


## Retrieve episodes



	GET /episodes


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update episode



	PUT /episodes/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Episode's name.</p>							|
| synopsis			| 			|  <p>Episode's synopsis.</p>							|
| airTime			| 			|  <p>Episode's airTime.</p>							|
| duration			| 			|  <p>Episode's duration.</p>							|

## Update Logged User&#39;s WatchedEpisodes list by Episode Id



	GET /episodes/updateWatched/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>Episode id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update Logged User&#39;s WatchlistedEpisodes list by Episode Id



	GET /episodes/updateWatchlisted/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>Episode id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

# Genre

## Create genre



	POST /genres


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Genre's name.</p>							|

## Delete genre



	DELETE /genres/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve genre



	GET /genres/:id


## Retrieve genres



	GET /genres


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update genre



	PUT /genres/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| name			| 			|  <p>Genre's name.</p>							|

# Media

## Create media



	POST /media


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| title			| 			|  <p>Media's title.</p>							|
| releaseDate			| 			|  <p>Media's releaseDate.</p>							|
| rating			| 			|  <p>Media's rating.</p>							|
| cast			| 			|  <p>Media's cast.</p>							|
| coverImage			| 			|  <p>Media's coverImage.</p>							|
| genre			| 			|  <p>Media's genre.</p>							|
| synopsis			| 			|  <p>Media's synopsis.</p>							|

## Delete media



	DELETE /media/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve media



	GET /media


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Adds an Actor to Media Cast list by Media Id



	GET /media/:id/addCast


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>Media id</p>							|
| cast			| String			|  <p>Actor</p>							|

## Update media



	PUT /media/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| title			| 			|  <p>Media's title.</p>							|
| releaseDate			| 			|  <p>Media's releaseDate.</p>							|
| rating			| 			|  <p>Media's rating.</p>							|
| cast			| 			|  <p>Media's cast.</p>							|
| coverImage			| 			|  <p>Media's coverImage.</p>							|
| genre			| 			|  <p>Media's genre.</p>							|
| synopsis			| 			|  <p>Media's synopsis.</p>							|

# Movie

## Create movie



	POST /movies


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Delete movie



	DELETE /movies/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve movie



	GET /movies/:id


## Retrieve movies



	GET /movies


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Get All Movies with associated Logged User&#39;s attributes



	GET /movies/user


## Update movie



	PUT /movies/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

# Photo

## Create photo



	POST /photos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| imgurlink			| 			|  <p>Photo's imgurlink.</p>							|
| deletehash			| 			|  <p>Photo's deletehash.</p>							|

## Delete photo



	DELETE /photos/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve photo



	GET /photos/:id


## Retrieve photos



	GET /photos


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update photo



	PUT /photos/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| imgurlink			| 			|  <p>Photo's imgurlink.</p>							|
| deletehash			| 			|  <p>Photo's deletehash.</p>							|

# Season

## Create season



	POST /seasons


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| number			| 			|  <p>Season's number.</p>							|
| episodes			| 			|  <p>Season's episodes.</p>							|

## Delete season



	DELETE /seasons/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve season



	GET /seasons/:id


## Retrieve seasons



	GET /seasons


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update season



	PUT /seasons/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| number			| 			|  <p>Season's number.</p>							|
| episodes			| 			|  <p>Season's episodes.</p>							|

# Series

## Create series



	POST /series


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|
| broadcaster			| 			|  <p>Series's broadcaster.</p>							|
| seasons			| 			|  <p>Series's seasons.</p>							|

## Delete series



	DELETE /series/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

## Retrieve series



	GET /series


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Get All Series with associated Logged User&#39;s attributes



	GET /series/user


## Update series



	PUT /series/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>admin access token.</p>							|

# User

## Create user



	POST /users


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>Master access_token.</p>							|
| email			| String			|  <p>User's email.</p>							|
| password			| String			|  <p>User's password.</p>							|
| name			| String			| **optional** <p>User's name.</p>							|
| picture			| String			| **optional** <p>User's picture.</p>							|
| role			| String			| **optional** <p>User's role.</p>							|

## Delete user



	DELETE /users/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## Retrieve Dashboard Media by User Id



	GET /users/:id/dashboard


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>User id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Retrieve Only Users in Logged User&#39;s friend list



	GET /users/befriended


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>User id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Retrieve Logged User Watchlist



	GET /users/myWatchlist


## Retrieve Genre Stats by User Id



	GET /users/:id/stats


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>User id</p>							|

## Retrieve Time Stats by User Id



	GET /users/:id/timeStats


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>User id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Retrieve current user



	GET /users/me


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|

## Show all episodes by Season Id



	GET /seasons/:id/episodes


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>Season id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Retrieve user



	GET /users/:id


## Retrieve users



	GET /users


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|

## Update password



	PUT /users/:id/password

### Headers

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| Authorization			| String			|  <p>Basic authorization with email and password.</p>							|

### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| password			| String			|  <p>User's new password.</p>							|

## Update user



	PUT /users/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| access_token			| String			|  <p>User access_token.</p>							|
| name			| String			| **optional** <p>User's name.</p>							|
| picture			| String			| **optional** <p>User's picture.</p>							|

## Update Logged User&#39;s Watched list by Media Id



	GET /users/updateWatched/:id


### Parameters

| Name    | Type      | Description                          |
|---------|-----------|--------------------------------------|
| id			| String			|  <p>Media id</p>							|
| q			| String			| **optional** <p>Query to search.</p>							|
| page			| Number			| **optional** <p>Page number.</p>							|
| limit			| Number			| **optional** <p>Amount of returned items.</p>							|
| sort			| String[]			| **optional** <p>Order of returned items.</p>							|
| fields			| String[]			| **optional** <p>Fields to be returned.</p>							|


