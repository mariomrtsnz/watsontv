import { OneMediaResponse } from './one-media-response';
import { OneEpisodeResponse } from './one-episode-response';
export interface OneSeasonResponse {
    id: string;
    series: OneMediaResponse;
    number: number;
    episodes: OneEpisodeResponse[];
}
