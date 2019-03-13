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
  media: OneMediaResponse[];
  alertMsg: string;

  constructor(private mediaService: MediaService, public dialog: MatDialog,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public authService: AuthenticationService) { }

  ngOnInit() {
    this.titleService.setTitle('Media');
    this.getAll('Success retrieving items');
  }

  getAll(alerMsg: string) {
    this.mediaService.getAll().subscribe(receivedMedia => this.media = receivedMedia.rows,
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
    this.router.navigate(['home/media/edit']);
  }

  openDialogDeleteMedia(m: OneMediaResponse) {
    const deleteMediaDialog = this.dialog.open(DialogDeleteMediaComponent,
      { panelClass: 'delete-dialog', data: { mediaId: m.id, mediaTitle: m.title } });

      deleteMediaDialog.afterClosed().subscribe(result => {
        if (result === 'confirm') {
          this.alertMsg = 'Genre deleted';
          this.getAll(this.alertMsg);
        }
      });
  }

  openMediaDetails(m: OneMediaResponse) {
    this.mediaService.selectedMedia = m;
    this.router.navigate(['home/media/details']);
  }

}
