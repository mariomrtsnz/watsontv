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
    min: 1
  },
  number: {
    type: Number,
    required: true,
    min: 1
  },
  season: {
    type: Schema.Types.ObjectId,
    ref: 'Season',
    required: true
  }
}, {
  strict: false,
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

episodeSchema.index({ number: 1, season: 1 }, { unique: true });

episodeSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      name: this.name,
      synopsis: this.synopsis,
      airTime: this.airTime,
      duration: this.duration,
      number: this.number,
      season: this.season,
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
