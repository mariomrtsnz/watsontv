import mongoose, { Schema } from 'mongoose'

const episodeSchema = new Schema({
  name: {
    type: String,
    required: true
  },
  synopsis: {
    type: String,
    required: true
  },
  airTime: {
    type: Date,
    required: true
  },
  duration: {
    type: Number,
    required: true,
    min: 1,
  }
}, {
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

episodeSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      name: this.name,
      synopsis: this.synopsis,
      airTime: this.airTime,
      duration: this.duration,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Episode', episodeSchema)

export const schema = model.schema
export default model
