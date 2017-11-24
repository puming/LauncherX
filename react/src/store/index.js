import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';

import {createStore, applyMiddleware, compose } from 'redux';
import rootReducer from '../reducers';

// const middlewares = [thunk.withExtraArgument()];
const middleware = applyMiddleware(thunk);
// let store = middleware(rootReducer());
let store = createStore(rootReducer(), undefined, middleware);

export default store;