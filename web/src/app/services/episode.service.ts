import { EpisodeDto } from './../dto/episode-dto';
import { environment } from './../../environments/environment';
import { AuthenticationService } from './authentication.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OneSeasonResponse } from '../interfaces/one-season-response';
import { OneEpisodeResponse } from '../interfaces/one-episode-response';
import { ResponseContainer } from '../interfaces/response-container';

const episodeUrl = `${environment.apiUrl}/episodes`;

@Injectable({
  providedIn: 'root'
})
export class EpisodeService {

  token = `?access_token=${this.authService.getToken()}`;
  public selectedSeason: OneSeasonResponse;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneEpisodeResponse>>(`${episodeUrl}${this.token}`);
  }

  getOne(id: string) {
    return this.http.get<OneEpisodeResponse>(`${episodeUrl}/${id}${this.token}`);
  }

  create(resource: EpisodeDto): Observable<OneEpisodeResponse> {
    return this.http.post<OneEpisodeResponse>(`${episodeUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneEpisodeResponse>> {
    return this.http.delete<ResponseContainer<OneEpisodeResponse>>(`${episodeUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: EpisodeDto): Observable<OneEpisodeResponse> {
    return this.http.put<OneEpisodeResponse>(`${episodeUrl}/${id}${this.token}`, resource);
  }
}
