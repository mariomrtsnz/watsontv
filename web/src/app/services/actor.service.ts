import { ActorDto } from './../dto/actor-dto';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';
import { HttpClient, HttpEvent, HttpRequest, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseContainer } from '../interfaces/response-container';
import { OneActorResponse } from '../interfaces/one-actor-response';

const actorUrl = `${environment.apiUrl}/actors`;

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  token = `?access_token=${this.authService.getToken()}`;

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  getAll() {
    return this.http.get<ResponseContainer<OneActorResponse>>(`${actorUrl}${this.token}`);
  }

  getAllSortedByName() {
    return this.http.get<ResponseContainer<OneActorResponse>>(`${actorUrl}?sort=name${this.token}`);
  }

  getOne(id: string) {
    return this.http.get<OneActorResponse>(`${actorUrl}/${id}${this.token}`);
  }

  create(resource: ActorDto): Observable<OneActorResponse> {
    return this.http.post<OneActorResponse>(`${actorUrl}${this.token}`, resource);
  }

  remove(id: string): Observable<ResponseContainer<OneActorResponse>> {
    return this.http.delete<ResponseContainer<OneActorResponse>>(`${actorUrl}/${id}${this.token}`);
  }

  edit(id: string, resource: ActorDto): Observable<OneActorResponse> {
    return this.http.put<OneActorResponse>(`${actorUrl}/${id}${this.token}`, resource);
  }

  uploadPicture(file: File): Observable<HttpEvent<any>> {

    const formData = new FormData();
    formData.append('actorPicture', file);

    const params = new HttpParams();

    const options = {
      params: params,
      reportProgress: true,
    };

    const req = new HttpRequest('POST', `${actorUrl}/picture${this.token}`, formData, options);
    return this.http.request(req);
  }
}
