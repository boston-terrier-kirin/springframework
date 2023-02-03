import React from 'react';

const Message = ({ type, children }) => {
  const cssClass =
    type === 'success' ? 'alert alert-success' : 'alert alert-danger';

  return (
    <div className={cssClass} role="alert">
      {children}
    </div>
  );
};

export default Message;
