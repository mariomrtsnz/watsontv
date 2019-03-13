import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../environments/environment.prod';
import { Injectable } from '@angular/core';
import { OneSeasonResponse } from '../interfaces/one-season-response';
import { ResponseContainer } from '../interfaces/response-container';
import { SeasonDto } from '../dto/season-dto';
import { OneEpisodeResponse } from '../interfaces/one-episode-response';

const seasonUrl = `${environment.apiUrl}/seasons`;

@Injectable({
  providedIn: 'root'
})
export class SeasonService {

  token = `?access_token=${this.authService.getToken()}`;
  public selectedSeason: OneSeasonResponse;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneSeasonResponse>>(`${seasonUrl}${this.token}`);
  }

  getAllEpisodes(id: string) {
    return this.http.get<OneEpisodeResponse[]>(`${seasonUrl}/${id}/episodes${this.token}`);
  }

  getOne(id: string) {
    return this.http.get<OneSeasonResponse>(`${seasonUrl}/${id}${this.token}`);
  }

  create(resource: SeasonDto): Observable<OneSeasonResponse> {
    return this.http.post<OneSeasonResponse>(`${seasonUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneSeasonResponse>> {
    return this.http.delete<ResponseContainer<OneSeasonResponse>>(`${seasonUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: SeasonDto): Observable<OneSeasonResponse> {
    return this.http.put<OneSeasonResponse>(`${seasonUrl}/${id}${this.token}`, resource);
  }
}
