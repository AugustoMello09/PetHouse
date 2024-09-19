import { Component, HostListener, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { jwtDecode } from 'jwt-decode';
import { NgxSpinnerService } from 'ngx-spinner';
import { debounceTime, Subject } from 'rxjs';
import { AuthModalComponent } from 'src/app/modals/auth-modal/auth-modal.component';
import { PlanoModalComponent } from 'src/app/modals/plano-modal/plano-modal.component';
import { Plano } from 'src/app/model/plano.model';
import { AuthService } from 'src/app/service/auth.service';
import { PlanoService } from 'src/app/service/plano.service';

@Component({
  selector: 'app-planos',
  templateUrl: './planos.component.html',
  styleUrls: ['./planos.component.css']
})
export class PlanosComponent implements OnInit {

  public paged: Plano[] = [];

  public planoSelecionadoId?: number;
  
  public currentPage: number = 0;
  public totalPages: number = 0;
  public pageSize: number = 3;

  public isLoading: boolean = false;

  private resizeSubject: Subject<number> = new Subject();

  constructor(private service: PlanoService, private dialog: MatDialog, private auth: AuthService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.adjustPageSize(window.innerWidth);
    this.loadPlanos();
    this.resizeSubject.pipe(debounceTime(300)).subscribe(width => {
      this.adjustPageSize(width);
      this.loadPlanos();
    });
    this.spinner.show();
  }

  adjustPageSize(width: number) {
    if (width <= 430) {
      this.pageSize = 1;
    } else if (width <= 1280) {
      this.pageSize = 2;
    } else {
      this.pageSize = 3;
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.adjustPageSize(event.target.innerWidth);
    this.loadPlanos();
  }

  public loadPlanos() {
    this.service.listAll(this.currentPage, this.pageSize).subscribe(
      res => {
        this.paged = res.content;
        this.totalPages = res.totalPages;
        if (this.paged.length == 0) { 
          this.isLoading = true;
        }
        this.spinner.hide();
      },
      () => {
        this.isLoading = true;
        this.spinner.hide();
      }
    );
  }

  public nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadPlanos();
    }
  }

  public previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadPlanos();
    }
  }

  public selecionarPlano(id: number) {
    this.dialog.open(PlanoModalComponent, {
      data: { id: id }
    });
  }

  public comprarPlano(id: number) {
    const isAuthenticated = this.auth.isAuthenticated();
    if (!isAuthenticated) {
      this.dialog.open(AuthModalComponent);
    } else {
      const token = this.auth.obterToken();
      if (token) {
        const decodedToken: any = jwtDecode(token);
        const userId = decodedToken.id; 
        this.service.comprarPlano(id, userId).subscribe(() => {
          this.message("Plano adquirido com sucesso! ");
        });
      }
    }
  }

  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }


}
