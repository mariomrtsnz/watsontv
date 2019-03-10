import { Media } from '.'

let media

beforeEach(async () => {
  media = await Media.create({ title: 'test', releaseDate: 'test', rating: 'test', cast: 'test', coverImage: 'test', genre: 'test', synopsis: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = media.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(media.id)
    expect(view.title).toBe(media.title)
    expect(view.releaseDate).toBe(media.releaseDate)
    expect(view.rating).toBe(media.rating)
    expect(view.cast).toBe(media.cast)
    expect(view.coverImage).toBe(media.coverImage)
    expect(view.genre).toBe(media.genre)
    expect(view.synopsis).toBe(media.synopsis)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = media.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(media.id)
    expect(view.title).toBe(media.title)
    expect(view.releaseDate).toBe(media.releaseDate)
    expect(view.rating).toBe(media.rating)
    expect(view.cast).toBe(media.cast)
    expect(view.coverImage).toBe(media.coverImage)
    expect(view.genre).toBe(media.genre)
    expect(view.synopsis).toBe(media.synopsis)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
