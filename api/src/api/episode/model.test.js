import { Episode } from '.'

let episode

beforeEach(async () => {
  episode = await Episode.create({ name: 'test', synopsis: 'test', airTime: 'test', duration: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = episode.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(episode.id)
    expect(view.name).toBe(episode.name)
    expect(view.synopsis).toBe(episode.synopsis)
    expect(view.airTime).toBe(episode.airTime)
    expect(view.duration).toBe(episode.duration)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = episode.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(episode.id)
    expect(view.name).toBe(episode.name)
    expect(view.synopsis).toBe(episode.synopsis)
    expect(view.airTime).toBe(episode.airTime)
    expect(view.duration).toBe(episode.duration)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
