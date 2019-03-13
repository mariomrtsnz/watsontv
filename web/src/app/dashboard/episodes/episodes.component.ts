import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { MatPaginator, MatSnackBar, MatDialog, MatTableDataSource } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { OneEpisodeResponse } from 'src/app/interfaces/one-episode-response';

@Component({
  selector: 'app-episodes',
  templateUrl: './episodes.component.html',
  styleUrls: ['./episodes.component.scss']
})
export class EpisodesComponent implements OnInit {
  displayedColumns: string[] = ['picture', 'name', 'actions'];
  dataSource;
  actors: any[] = [];
  alertMsg: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private episodeService: EpisodeService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.titleService.setTitle('Episodes');
    this.getAllEpisodes('Success retrieving items.');
  }

  getAllEpisodes(message: string) {
    this.episodeService.getAll().subscribe(receivedActors => {
      this.dataSource = new MatTableDataSource(receivedActors.rows);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewEpisode() {
    const newEpisodeDialog = this.dialog.open(DialogEpisodeComponent, { panelClass: 'add-dialog' });

    newEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Episode created';
        this.getAllEpisodes(this.alertMsg);
      }
    });
  }

  openDialogDeleteEpisode(actor: OneEpisodeResponse) {
    const deleteEpisodeDialog = this.dialog.open(DialogDeleteEpisodeComponent,
      { panelClass: 'delete-dialog', data: { actorId: actor.id, actorName: actor.name } });

      deleteEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Episode deleted';
        this.getAllEpisodes(this.alertMsg);
      }
    });

  }

  openDialogEditEpisode(actor: OneEpisodeResponse) {
    const updateEpisodeDialog = this.dialog.open(DialogEpisodeComponent, { panelClass: 'add-dialog', data: { actor: actor } });

    updateEpisodeDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Actor updated';
        this.getAllEpisodes(this.alertMsg);
      }
    });
  }

}
