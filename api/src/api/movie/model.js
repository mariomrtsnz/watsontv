import mongoose, { Schema } from 'mongoose'
import { Media } from '../media';

const movieSchema = new Schema({
  runtime: {
    type: Number,
    min: 30
  }
}, { strict: false, timestamps: true });

movieSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      title: this.title,
      releaseDate: this.releaseDate,
      rating: this.rating,
      cast: this.cast,
      coverImage: this.coverImage,
      genre: this.genre,
      synopsis: this.synopsis,
      runtime: this.runtime,
      mediaType: this.__t,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = Media.discriminator('Movie', movieSchema)

export const schema = model.schema
export default model
