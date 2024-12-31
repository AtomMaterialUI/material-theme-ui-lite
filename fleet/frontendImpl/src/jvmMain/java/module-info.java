module fleet.sample.frontendImpl {
  requires fleet.frontend;
  requires fleet.kernel;
  requires fleet.util.logging.api;
  requires fleet.rhizomedb;
  requires fleet.frontend.ui;

  exports com.mallowigi.material.fleet;
  provides fleet.kernel.plugins.Plugin with com.mallowigi.material.fleet.MaterialThemePlugin;
}
