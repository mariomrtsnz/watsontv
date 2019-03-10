import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Media } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, media

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  media = await Media.create({})
})

test('POST /media 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, title: 'test', releaseDate: 'test', rating: 'test', cast: 'test', coverImage: 'test', genre: 'test', synopsis: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.title).toEqual('test')
  expect(body.releaseDate).toEqual('test')
  expect(body.rating).toEqual('test')
  expect(body.cast).toEqual('test')
  expect(body.coverImage).toEqual('test')
  expect(body.genre).toEqual('test')
  expect(body.synopsis).toEqual('test')
})

test('POST /media 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /media 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /media 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /media/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${media.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(media.id)
})

test('GET /media/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /media/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${media.id}`)
    .send({ access_token: adminSession, title: 'test', releaseDate: 'test', rating: 'test', cast: 'test', coverImage: 'test', genre: 'test', synopsis: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(media.id)
  expect(body.title).toEqual('test')
  expect(body.releaseDate).toEqual('test')
  expect(body.rating).toEqual('test')
  expect(body.cast).toEqual('test')
  expect(body.coverImage).toEqual('test')
  expect(body.genre).toEqual('test')
  expect(body.synopsis).toEqual('test')
})

test('PUT /media/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${media.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /media/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${media.id}`)
  expect(status).toBe(401)
})

test('PUT /media/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, title: 'test', releaseDate: 'test', rating: 'test', cast: 'test', coverImage: 'test', genre: 'test', synopsis: 'test' })
  expect(status).toBe(404)
})

test('DELETE /media/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${media.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /media/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${media.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /media/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${media.id}`)
  expect(status).toBe(401)
})

test('DELETE /media/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
