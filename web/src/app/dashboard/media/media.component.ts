import { AuthenticationService } from './../../services/authentication.service';
import { Title } from '@angular/platform-browser';
import { MatDialog, MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { OneMediaResponse } from 'src/app/interfaces/one-media-response';
import { MediaService } from 'src/app/services/media.service';
import { DialogDeleteMediaComponent } from 'src/app/dialogs/dialog-delete-media/dialog-delete-media.component';

@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.scss']
})
export class MediaComponent implements OnInit {
  media: OneMediaResponse[];

  constructor(private mediaService: MediaService, public dialog: MatDialog,
    public router: Router, public snackBar: MatSnackBar, private titleService: Title, public authService: AuthenticationService) { }

  ngOnInit() {
    this.titleService.setTitle('Media');
    this.getAll();
  }

  /** Get the list of all POIs from API */
  getAll() {
    this.mediaService.getAll().subscribe(receivedMedia => this.media = receivedMedia.rows,
      err => this.snackBar.open('There was an error when we were loading data.', 'Close', { duration: 3000 }));
  }

  /** Go to PoiCreate view */
  openNewMedia() {
    this.router.navigate(['home/create']);
  }

  openEditMedia(m: OneMediaResponse) {
    this.mediaService.selectedMedia = m;
    this.router.navigate(['home/edit']);
  }

  openDialogDeleteMedia(m: OneMediaResponse) {
    const dialogDeletePoi = this.dialog.open(DialogDeleteMediaComponent, { data: { media: m } });
    dialogDeletePoi.afterClosed().subscribe(res => res === 'confirm' ? this.getAll() : null,
      err => this.snackBar.open('There was an error when we were deleting this Media.', 'Close', { duration: 3000 }));
  }

  /** Go to POIDetails view */
  openMediaDetails(m: OneMediaResponse) {
    this.mediaService.selectedMedia = m;
    this.router.navigate(['home/details']);
  }

}
