import { UserResponse } from './user-response';
import { OneMediaResponse } from './one-media-response';
export interface OneCollectionResponse {
    id: string;
    name: string;
    description: string;
    media: OneMediaResponse[];
    owner: UserResponse;
}
