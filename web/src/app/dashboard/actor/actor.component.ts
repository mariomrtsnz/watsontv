import { Router } from '@angular/router';
import { ActorService } from './../../services/actor.service';
import { Title } from '@angular/platform-browser';
import { MatPaginator, MatSnackBar, MatDialog, MatTableDataSource } from '@angular/material';
import { DialogDeleteActorComponent } from './../../dialogs/dialog-delete-actor/dialog-delete-actor.component';
import { DialogActorComponent } from './../../dialogs/dialog-actor/dialog-actor.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { OneActorResponse } from 'src/app/interfaces/one-actor-response';

@Component({
  selector: 'app-actor',
  templateUrl: './actor.component.html',
  styleUrls: ['./actor.component.scss']
})
export class ActorComponent implements OnInit {
  displayedColumns: string[] = ['picture', 'name', 'actions'];
  dataSource;
  actors: any[] = [];
  alertMsg: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private actorService: ActorService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.titleService.setTitle('Actors');
    this.getAllActors('Success retrieving items.');
  }

  getAllActors(message: string) {
    this.actorService.getAll().subscribe(receivedActors => {
      this.dataSource = new MatTableDataSource(receivedActors.rows);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewActor() {
    const newActorDialog = this.dialog.open(DialogActorComponent, { panelClass: 'add-dialog' });

    newActorDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Actor created';
        this.getAllActors(this.alertMsg);
      }
    });
  }

  openDialogDeleteActor(actor: OneActorResponse) {
    const deleteActorDialog = this.dialog.open(DialogDeleteActorComponent,
      { panelClass: 'delete-dialog', data: { actorId: actor.id, actorName: actor.name } });

      deleteActorDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Actor deleted';
        this.getAllActors(this.alertMsg);
      }
    });

  }

  openDialogEditActor(actor: OneActorResponse) {
    const updateActorDialog = this.dialog.open(DialogActorComponent, { panelClass: 'add-dialog', data: { actor: actor } });

    updateActorDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'Actor updated';
        this.getAllActors(this.alertMsg);
      }
    });
  }

}
