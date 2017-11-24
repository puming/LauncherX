import Router from '../Router';

const navReducer = (state, action) => {
  const newState = Router.router.getStateForAction(action, state);
  return newState || state;
};

export default navReducer;