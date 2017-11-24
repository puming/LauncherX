/**
 * Created by pm on 17-8-9.
 */
import React, {Component} from 'react';
import store from './store/index';
import {connect, Provider} from 'react-redux';
import Main from "./Main";
/*@connect(state => ({
 nav: state.nav
 }))*/


const mapStateToProps = state => ({
  nav: state.nav
});
const AppNavigation = connect(mapStateToProps)(Main);

export default class App extends Component {

  render() {
    return (
      <Provider store={store}>
        <AppNavigation/>
      </Provider>
    )
  }
}

