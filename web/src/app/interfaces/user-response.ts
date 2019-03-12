import { OneMediaResponse } from './one-media-response';
import { OneGenreResponse } from './one-genre-response';

export interface UserResponse {
    role: string;
    follows: OneMediaResponse[];
    likes: OneGenreResponse[];
    watched: OneMediaResponse[];
    watchlist: OneMediaResponse[];
    friends: UserResponse[];
    id: string;
    wishlist: OneMediaResponse[];
    picture: string;
    name: string;
    email: string;
    password: string;
    createdAt: Date;
}
