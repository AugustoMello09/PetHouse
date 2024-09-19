import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import * as L from 'leaflet';
import { NgxSpinnerService } from 'ngx-spinner';
import { Cidade } from 'src/app/model/cidade.model';
import { Estado } from 'src/app/model/estado.model';
import { EnderecoService } from 'src/app/service/endereco.service';

@Component({
  selector: 'app-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit {

  private map!: L.Map;
  private centroid: L.LatLngExpression = [42.3601, -71.0589]; 

  public estadoLista: Estado[] = [];
  public cidadeLista: Cidade[] = [];
  public estadoSelecionado: number = 0;
  public cidadeSelecionada = '';

  constructor(private dialog: MatDialog, private router: Router,
    private cep: EnderecoService, private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.initMap();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
       setTimeout(() => {  initFlowbite();})
      }
    });
    this.getBuscaEstado();
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: this.centroid,
      zoom: 12
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 10,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);
  }

  public close() {
    this.dialog.closeAll();
  }

  public getBuscaEstado(): void {
    this.spinner.show();
    this.cep.getConsultaEstados().subscribe(response => {
      const filtroEstadoId: Estado[] = response.map(estado => ({
        id: estado.id,
        nome: estado.nome
      }));
      this.estadoLista = filtroEstadoId;
      this.spinner.hide();
    });
  }

  public getBuscaCidade(event: any): void {
    this.spinner.show();
    const selectedUfId = event.target.value;
    this.estadoSelecionado = selectedUfId;
    this.cep.getConsultaCidades(this.estadoSelecionado).subscribe(response => {
      const cidadesFiltradas: Cidade[] = response.map(cidade => ({
        nome: cidade.nome
      }));
      this.cidadeLista = cidadesFiltradas;
      this.spinner.hide();
    });
  }

  public onCidadeSelecionada(event: any): void {
    this.cidadeSelecionada = event.target.value;
  }
  
  public atualizarMapa(): void {
    if (this.cidadeSelecionada) {
      this.spinner.show();
      this.cep.getCoordenadasCidade(this.cidadeSelecionada).subscribe(response => {
        if (response && response.length > 0) {
          const coordenadas = response[0]; 
          const latitude = coordenadas.lat;
          const longitude = coordenadas.lon;
          this.updateMapPosition(latitude, longitude);
          this.spinner.hide();
        } 
      });
    } else {
      console.log('Nenhuma cidade selecionada.');
    }
  }
  
  private updateMapPosition(lat: number, lon: number): void {
    const newLatLng = L.latLng(lat, lon);
    this.map.setView(newLatLng, 14);
  }

}
