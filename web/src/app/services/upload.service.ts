import { AuthenticationService } from 'src/app/services/authentication.service';
import { environment } from 'src/environments/environment';
import { ActorDto } from './../dto/actor-dto';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpRequest, HttpEventType, HttpResponse } from '@angular/common/http';

const actorUrl = `${environment.apiUrl}/actors`;
@Injectable({
    providedIn: 'root'
})
export class UploadService {

    token = `?access_token=${this.authService.getToken()}`;

    constructor(private http: HttpClient, private authService: AuthenticationService) { }

    public upload(files: Set<File>, form: ActorDto): { [key: string]: Observable<number> } {
        const status = {};

        files.forEach(file => {
            const formData: FormData = new FormData();
            formData.append('picture', file, file.name);
            formData.append('name', form.name);
            console.log(`${actorUrl}?${this.token}`);
            const req = new HttpRequest('POST', `${actorUrl}${this.token}`, formData, {
                reportProgress: true,
            });
            const progress = new Subject<number>();
            const startTime = new Date().getTime();
            this.http.request(req).subscribe(event => {
                if (event.type === HttpEventType.UploadProgress) {
                    const percentDone = Math.round((100 * event.loaded) / event.total);
                    progress.next(percentDone);
                } else if (event instanceof HttpResponse) {
                    progress.complete();
                }
            });
            status[file.name] = {
                progress: progress.asObservable()
            };
        });
        return status;
  }
}
