import { Collection } from '.'

let collection

beforeEach(async () => {
  collection = await Collection.create({ name: 'test', description: 'test', collected: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = collection.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(collection.id)
    expect(view.name).toBe(collection.name)
    expect(view.description).toBe(collection.description)
    expect(view.collected).toBe(collection.collected)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = collection.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(collection.id)
    expect(view.name).toBe(collection.name)
    expect(view.description).toBe(collection.description)
    expect(view.collected).toBe(collection.collected)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
