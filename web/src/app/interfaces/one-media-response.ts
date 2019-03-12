import { OneGenreResponse } from './one-genre-response';
import { OneActorResponse } from './one-actor-response';
import { OneSeasonResponse } from './one-season-response';

export interface OneMediaResponse {
    id: string;
    title: string;
    releaseDate: Date;
    rating: number[];
    cast: OneActorResponse[];
    coverImage: string;
    genre: OneGenreResponse;
    synopsis: string;
    mediaType: string;
    broadcaster: string;
    seasons: OneSeasonResponse[];
}
