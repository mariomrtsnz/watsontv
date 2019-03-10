import { Actor } from '.'

let actor

beforeEach(async () => {
  actor = await Actor.create({ name: 'test', picture: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = actor.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(actor.id)
    expect(view.name).toBe(actor.name)
    expect(view.picture).toBe(actor.picture)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = actor.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(actor.id)
    expect(view.name).toBe(actor.name)
    expect(view.picture).toBe(actor.picture)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
