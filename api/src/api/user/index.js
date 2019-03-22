import { Router } from 'express'
import { middleware as query, Schema } from 'querymen'
import { middleware as body } from 'bodymen'
import { password as passwordAuth, master, token } from '../../services/passport'
import { index, showMe, show, create, update, updatePassword, destroy, getTotalWatchedTime, befriended, updateWatched, updateWatchlisted, editFriended, getWatchlist, getGenreStats, getDashboardMedia } from './controller'
import { schema } from './model'
export User, { schema } from './model'

const router = new Router()
const { email, password, name, picture, role, friends, dateOfBirth } = schema.tree
const userSchema = new Schema({
  name: {
    type: RegExp,
    paths: ['name']
  }
})

/**
 * @api {get} /users Retrieve users
 * @apiName RetrieveUsers
 * @apiGroup User
 * @apiPermission admin
 * @apiPermission user
 * @apiParam {String} access_token User access_token.
 * @apiUse listParams
 * @apiSuccess {Object[]} users List of users.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 Admin access only.
 */
router.get('/',
  token({ required: true, roles: ['admin', 'user'] }),
  query(userSchema),
  index)

/**
 * @api {get} /users/:id/dashboard Retrieve Dashboard Media by User Id
 * @apiName GetDashboardMedia
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id User id
 * @apiUse listParams
 * @apiSuccess {Object[]} Media.
 * @apiError {Object} 400 Empty user watchedEpisodes.
 */
router.get('/:id/dashboard',
  token({ required: true }),
  getDashboardMedia)

/**
 * @api {get} /users/:id/timeStats Retrieve Time Stats by User Id
 * @apiName GetUserTimeStats
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id User id
 * @apiUse listParams
 * @apiSuccess {Object} timeStats
 */
router.get('/:id/timeStats',
  token({ required: true }),
  getTotalWatchedTime)

  /**
 * @api {get} /users/myWatchlist Retrieve Logged User Watchlist
 * @apiName GetMyWatchlist
 * @apiGroup User
 * @apiPermission token
 * @apiSuccess {Object[]} Media.
 */
router.get('/myWatchlist',
  token({ required: true }),
  getWatchlist)

  /**
 * @api {get} /users/:id/stats Retrieve Genre Stats by User Id
 * @apiName GetUserGenreStats
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id User id
 * @apiSuccess {Object[]} genreStats.
 */
router.get('/:id/stats',
  token({ required: true }),
  getGenreStats)

/**
 * @api {get} /users/me Retrieve current user
 * @apiName RetrieveCurrentUser
 * @apiGroup User
 * @apiPermission user
 * @apiParam {String} access_token User access_token.
 * @apiSuccess {Object} user User's data.
 */
router.get('/me',
  token({ required: true }),
  showMe)

  /**
 * @api {get} /users/befriended Retrieve Only Users in Logged User's friend list
 * @apiName GetFriends
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id User id
 * @apiUse listParams
 * @apiSuccess {Object[]} Users.
 */
router.get('/befriended',
  token({ required: true }),
  query(userSchema),
  befriended)

/**
 * @api {get} /users/:id Retrieve user
 * @apiName RetrieveUser
 * @apiGroup User
 * @apiPermission public
 * @apiSuccess {Object} user User's data.
 * @apiError 404 User not found.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {post} /users Create user
 * @apiName CreateUser
 * @apiGroup User
 * @apiPermission master
 * @apiParam {String} access_token Master access_token.
 * @apiParam {String} email User's email.
 * @apiParam {String{6..}} password User's password.
 * @apiParam {String} [name] User's name.
 * @apiParam {String} [picture] User's picture.
 * @apiParam {String=user,admin} [role=user] User's role.
 * @apiSuccess (Sucess 201) {Object} user User's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 Master access only.
 * @apiError 409 Email already registered.
 */
router.post('/',
  master(),
  body({ email, password, name, picture, role }),
  create)

/**
 * @api {put} /users/:id Update user
 * @apiName UpdateUser
 * @apiGroup User
 * @apiPermission user
 * @apiParam {String} access_token User access_token.
 * @apiParam {String} [name] User's name.
 * @apiParam {String} [picture] User's picture.
 * @apiSuccess {Object} user User's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 Current user or admin access only.
 * @apiError 404 User not found.
 */
router.put('/:id',
  token({ required: true }),
  body({ email, name, role }),
  update)

// router.put('/:id',
//   token({ required: true }),
//   body({ email, name, picture }),
//   update)

/**
 * @api {put} /users/:id/password Update password
 * @apiName UpdatePassword
 * @apiGroup User
 * @apiHeader {String} Authorization Basic authorization with email and password.
 * @apiParam {String{6..}} password User's new password.
 * @apiSuccess (Success 201) {Object} user User's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 Current user access only.
 * @apiError 404 User not found.
 */
router.put('/:id/password',
  passwordAuth(),
  body({ password }),
  updatePassword)

/**
 * @api {delete} /users/:id Delete user
 * @apiName DeleteUser
 * @apiGroup User
 * @apiPermission admin
 * @apiParam {String} access_token User access_token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 401 Admin access only.
 * @apiError 404 User not found.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

/**
 * @api {get} /users/updateWatched/:id Update Logged User's Watched list by Media Id
 * @apiName UpdateWatched
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id Media id
 * @apiUse listParams
 * @apiSuccess {Object} User.
 */
router.put('/updateWatched/:id',
  token({required: true}),
  updateWatched)

  /**
 * @api {get} /users/updateWatchlisted/:id Update Logged User's Watchlist list by Media Id
 * @apiName UpdateWatched
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id Media id
 * @apiUse listParams
 * @apiSuccess {Object} User.
 */
router.put('/updateWatchlisted/:id',
  token({required: true}),
  updateWatchlisted)

  /**
 * @api {get} /users/updateFriended/:id Update Logged User's Friends list by User Id
 * @apiName UpdateWatched
 * @apiGroup User
 * @apiPermission token
 * @apiParam {String} id Media id
 * @apiUse listParams
 * @apiSuccess {Object} User.
 * @apiError {Object} 400 Empty user watchedEpisodes.
 */
router.put('/updateFriended/:id',
  token({ required: true }),
  editFriended)

export default router
