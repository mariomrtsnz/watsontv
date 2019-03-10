import mongoose, { Schema } from 'mongoose'

const actorSchema = new Schema({
  name: {
    type: String
  },
  picture: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

actorSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      name: this.name,
      picture: this.picture,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Actor', actorSchema)

export const schema = model.schema
export default model
