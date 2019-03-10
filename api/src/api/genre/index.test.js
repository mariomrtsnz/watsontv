import request from 'supertest'
import { apiRoot } from '../../config'
import { signSync } from '../../services/jwt'
import express from '../../services/express'
import { User } from '../user'
import routes, { Genre } from '.'

const app = () => express(apiRoot, routes)

let userSession, adminSession, genre

beforeEach(async () => {
  const user = await User.create({ email: 'a@a.com', password: '123456' })
  const admin = await User.create({ email: 'c@c.com', password: '123456', role: 'admin' })
  userSession = signSync(user.id)
  adminSession = signSync(admin.id)
  genre = await Genre.create({})
})

test('POST /genres 201 (admin)', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: adminSession, name: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.name).toEqual('test')
})

test('POST /genres 401 (user)', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('POST /genres 401', async () => {
  const { status } = await request(app())
    .post(`${apiRoot}`)
  expect(status).toBe(401)
})

test('GET /genres 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /genres/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${genre.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(genre.id)
})

test('GET /genres/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /genres/:id 200 (admin)', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${genre.id}`)
    .send({ access_token: adminSession, name: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(genre.id)
  expect(body.name).toEqual('test')
})

test('PUT /genres/:id 401 (user)', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${genre.id}`)
    .send({ access_token: userSession })
  expect(status).toBe(401)
})

test('PUT /genres/:id 401', async () => {
  const { status } = await request(app())
    .put(`${apiRoot}/${genre.id}`)
  expect(status).toBe(401)
})

test('PUT /genres/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ access_token: adminSession, name: 'test' })
  expect(status).toBe(404)
})

test('DELETE /genres/:id 204 (admin)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${genre.id}`)
    .query({ access_token: adminSession })
  expect(status).toBe(204)
})

test('DELETE /genres/:id 401 (user)', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${genre.id}`)
    .query({ access_token: userSession })
  expect(status).toBe(401)
})

test('DELETE /genres/:id 401', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${genre.id}`)
  expect(status).toBe(401)
})

test('DELETE /genres/:id 404 (admin)', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
    .query({ access_token: adminSession })
  expect(status).toBe(404)
})
