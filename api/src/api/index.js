import { Router } from 'express'
import user from './user'
import auth from './auth'
import movie from './movie'
import series from './series'
import season from './season'
import episode from './episode'
import genre from './genre'
import actor from './actor'
import collection from './collection'
import media from './media'
import photo from './photo'

const router = new Router()

/**
 * @apiDefine master Master access only
 * You must pass `access_token` parameter or a Bearer Token authorization header
 * to access this endpoint.
 */
/**
 * @apiDefine admin Admin access only
 * You must pass `access_token` parameter or a Bearer Token authorization header
 * to access this endpoint.
 */
/**
 * @apiDefine user User access only
 * You must pass `access_token` parameter or a Bearer Token authorization header
 * to access this endpoint.
 */
/**
 * @apiDefine listParams
 * @apiParam {String} [q] Query to search.
 * @apiParam {Number{1..30}} [page=1] Page number.
 * @apiParam {Number{1..100}} [limit=30] Amount of returned items.
 * @apiParam {String[]} [sort=-createdAt] Order of returned items.
 * @apiParam {String[]} [fields] Fields to be returned.
 */
router.use('/users', user)
router.use('/auth', auth)
router.use('/movies', movie)
router.use('/series', series)
router.use('/seasons', season)
router.use('/episodes', episode)
router.use('/genres', genre)
router.use('/actors', actor)
router.use('/collections', collection)
router.use('/media', media)
router.use('/photos', photo)

export default router
