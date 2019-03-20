import { ResponseContainer } from './../../interfaces/response-container';
import { AuthenticationService } from './../../services/authentication.service';
import { Title } from '@angular/platform-browser';
import { MatDialog, MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { OneMediaResponse } from 'src/app/interfaces/one-media-response';
import { MediaService } from 'src/app/services/media.service';
import { DialogDeleteMediaComponent } from 'src/app/dialogs/dialog-delete-media/dialog-delete-media.component';
import { DialogMediaTypeComponent } from 'src/app/dialogs/dialog-media-type/dialog-media-type.component';

@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.scss']
})
export class MediaComponent implements OnInit {
  media: ResponseContainer<OneMediaResponse>;
  alertMsg: string;
  onlyMovies = false;
  p = 1;

  constructor(private mediaService: MediaService, public dialog: MatDialog,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public authService: AuthenticationService) { }

  ngOnInit() {
    this.titleService.setTitle('Media');
    this.getAll(this.p);
  }

  getAll(page: number) {
    this.p = page;
    this.mediaService.getAll(page).subscribe(receivedMedia => this.media = receivedMedia,
      err => this.snackBar.open('There was an error when we were loading data.', 'Close', { duration: 3000 }));
  }

  openNewMedia() {
    const createMediaTypeDialog = this.dialog.open(DialogMediaTypeComponent,
      { panelClass: 'delete-dialog' });

      createMediaTypeDialog.afterClosed().subscribe(result => {
        this.mediaService.mediaType = result;
        this.router.navigate(['home/media/create']);
      });
  }

  openEditMedia(m: OneMediaResponse) {
    this.mediaService.selectedMedia = m;
    this.mediaService.mediaType = m.mediaType;
    this.router.navigate(['home/media/edit']);
  }

  openDialogDeleteMedia(m: OneMediaResponse) {
    const deleteMediaDialog = this.dialog.open(DialogDeleteMediaComponent,
      { panelClass: 'delete-dialog', data: { mediaId: m.id, mediaTitle: m.title, mediaType: m.mediaType } });

      deleteMediaDialog.afterClosed().subscribe(result => {
        if (result === 'confirm') {
          this.alertMsg = 'Genre deleted';
          this.getAll(this.p);
        }
      });
  }

  openMediaDetails(m: OneMediaResponse) {
    this.mediaService.selectedMedia = m;
    this.router.navigate(['home/media/details']);
  }

}
