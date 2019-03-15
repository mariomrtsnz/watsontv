import { Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OneMediaResponse } from '../interfaces/one-media-response';
import { ResponseContainer } from '../interfaces/response-container';
import { SeriesDto } from '../dto/series-dto';
import { MovieDto } from '../dto/movie-dto';

const mediaUrl = `${environment.apiUrl}/media`;
const seriesUrl = `${environment.apiUrl}/series`;
const movieUrl = `${environment.apiUrl}/movies`;

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  token = `?access_token=${this.authService.getToken()}`;
  public selectedMedia: OneMediaResponse;
  public mediaType: string;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll(): Observable<ResponseContainer<OneMediaResponse>> {
    return this.http.get<ResponseContainer<OneMediaResponse>>(`${mediaUrl}${this.token}`);
  }

  addCastMember(actorId: string): any {
    const cast = {'cast': actorId};
    return this.http.put<OneMediaResponse>(`${mediaUrl}/${this.selectedMedia.id}/addCast${this.token}`, cast);
  }

  removeCastMember(actorId: string): any {
    const cast = {'cast': actorId};
    return this.http.put<OneMediaResponse>(`${mediaUrl}/${this.selectedMedia.id}/removeCast${this.token}`, cast);
  }

  getOne(id: string): Observable<OneMediaResponse> {
    return this.http.get<OneMediaResponse>(`${mediaUrl}/${id}${this.token}`);
  }

  createSeries(resource: SeriesDto): Observable<OneMediaResponse> {
    return this.http.post<OneMediaResponse>(`${seriesUrl}${this.token}`, resource);
  }

  createMovie(resource: MovieDto): Observable<OneMediaResponse> {
    return this.http.post<OneMediaResponse>(`${movieUrl}${this.token}`, resource);
  }

  removeSeries(id: string): Observable<OneMediaResponse[]> {
    return this.http.delete<OneMediaResponse[]>(`${seriesUrl}/${id}${this.token}`);
  }

  removeMovie(id: string): Observable<OneMediaResponse[]> {
    return this.http.delete<OneMediaResponse[]>(`${movieUrl}/${id}${this.token}`);
  }

  editMovie(id: string, resource: MovieDto): Observable<OneMediaResponse> {
    return this.http.put<OneMediaResponse>(`${movieUrl}/${id}${this.token}`, resource);
  }

  editSeries(id: string, resource: SeriesDto): Observable<OneMediaResponse> {
    return this.http.put<OneMediaResponse>(`${seriesUrl}/${id}${this.token}`, resource);
  }

}
